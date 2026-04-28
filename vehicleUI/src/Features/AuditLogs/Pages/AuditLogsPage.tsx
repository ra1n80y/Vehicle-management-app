import { useEffect, useState } from "react";
import AuditLogTable from "../Components/AuditLogTable";
import { AuditLogService } from "../Services/AuditLogService";
import type { AuditLog } from "../Types/AuditLog";
import CommonModal from "../../../Shared/Components/CommonModal";
import { formatTimestamp } from "../../../Shared/Utils/formatDate";
import "./AuditLogsPage.css";

const AuditLogsPage = () => {
  const [auditLogs, setAuditLogs] = useState<AuditLog[]>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [selectedLog, setSelectedLog] = useState<AuditLog | null>(null);

  useEffect(() => {
    const fetchAuditLogs = async () => {
      try {
        setIsLoading(true);
        setError(null);
        const data = await AuditLogService.getAuditLogs();
        setAuditLogs(data);
      } catch (err) {
        if (err instanceof Error) {
          setError(err.message);
        } else {
          setError("Failed to load audit logs.");
        }
      } finally {
        setIsLoading(false);
      }
    };
    void fetchAuditLogs();
  }, []);

  const handleRowClick = (log: AuditLog) => {
    setSelectedLog(log);
  };

  const handleCloseModal = () => {
    setSelectedLog(null);
  };

  return (
    <section className="audit-logs-page">
      <div className="audit-logs-page__header">
        <h2>Audit Logs</h2>
        <p>
          Read-only visibility into successful mutation activity across the
          system.
        </p>
      </div>

      {isLoading && (
        <div className="audit-logs-page__state">Loading audit logs...</div>
      )}

      {!isLoading && error && (
        <div className="audit-logs-page__state audit-logs-page__state--error">
          {error}
        </div>
      )}

      {!isLoading && !error && auditLogs.length === 0 && (
        <div className="audit-logs-page__state">No audit logs found.</div>
      )}

      {!isLoading && !error && auditLogs.length > 0 && (
        <AuditLogTable auditLogs={auditLogs} onRowClick={handleRowClick} />
      )}

      <CommonModal
        show={!!selectedLog}
        title={`Audit Log #${selectedLog?.id ?? ""}`}
        onClose={handleCloseModal}
      >
        {selectedLog && (
          <div className="audit-log-detail">
            <p>
              <strong>Action:</strong> {selectedLog.action}
            </p>
            <p>
              <strong>Resource Type:</strong> {selectedLog.resourceType}
            </p>
            <p>
              <strong>Resource ID:</strong> {selectedLog.resourceId}
            </p>
            <p>
              <strong>Performed By:</strong> {selectedLog.performedBy}
            </p>
            <p>
              <strong>Performed At:</strong>{" "}
              {formatTimestamp(selectedLog.performedAt)}
            </p>
          </div>
        )}
      </CommonModal>
    </section>
  );
};

export default AuditLogsPage;
