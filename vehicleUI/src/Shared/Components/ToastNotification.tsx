import { useEffect } from "react";

interface ToastProps {
  message: string;
  show: boolean;
  onClose: () => void;
}

function ToastNotification({ message, show, onClose }: ToastProps) {
  useEffect(() => {
    if (show) {
      const timer = setTimeout(() => {
        onClose();
      }, 3000);
      return () => clearTimeout(timer);
    }
  }, [show, onClose]);

  if (!show) return null;

  return (
    <div className="toast show position-fixed bottom-0 end-0 m-4" role="alert">
      <div className="toast-header">
        <strong className="me-auto">Success</strong>
        <button type="button" className="btn-close" onClick={onClose} />
      </div>
      <div className="toast-body">{message}</div>
    </div>
  );
}

export default ToastNotification;
