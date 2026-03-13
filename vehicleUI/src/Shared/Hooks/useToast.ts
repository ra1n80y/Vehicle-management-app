import { useState } from "react";

export default function useToast() {
  const [message, setMessage] = useState("");
  const [show, setShow] = useState(false);

  const triggerToast = (msg: string) => {
    setMessage(msg);
    setShow(true);
  };

  const closeToast = () => {
    setShow(false);
  };

  return {
    message,
    show,
    triggerToast,
    closeToast,
  };
}