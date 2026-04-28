import type { AuditLog } from "../Types/AuditLog";
import { formatTimestamp } from "../../../Shared/Utils/formatDate";
import "./AuditLogTable.css";

type AuditLogTableProps = {
  auditLogs: AuditLog[];
  onRowClick?: (log: AuditLog) => void;
};

const AuditLogTable = ({ auditLogs, onRowClick }: AuditLogTableProps) => {
  return (
    <div className="audit-log-table-wrapper">
      <table className="audit-log-table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Action</th>
            <th>Resource Type</th>
            <th>Resource ID</th>
            <th>Performed By</th>
            <th>Performed At</th>
          </tr>
        </thead>
        <tbody>
          {auditLogs.map((log) => (
            <tr
              key={log.id}
              onClick={() => onRowClick?.(log)}
              style={{ cursor: onRowClick ? "pointer" : "default" }}
            >
              <td>{log.id}</td>
              <td>{log.action}</td>
              <td>{log.resourceType}</td>
              <td>{log.resourceId}</td>
              <td>{log.performedBy}</td>
              <td>{formatTimestamp(log.performedAt)}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default AuditLogTable;
