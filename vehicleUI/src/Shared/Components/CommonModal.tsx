import { Modal } from "react-bootstrap";
import type { ReactNode } from "react";

type Props = {
  show: boolean;
  title: string;
  onClose: () => void;
  children: ReactNode;
};

export default function CommonModal({ show, title, onClose, children }: Props) {
  return (
    <Modal show={show} onHide={onClose} centered>
      <Modal.Header closeButton>
        <Modal.Title>{title}</Modal.Title>
      </Modal.Header>

      <Modal.Body>{children}</Modal.Body>
    </Modal>
  );
}
