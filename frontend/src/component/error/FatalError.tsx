import Modal from "../modal/Modal.tsx";
import "../../assets/css/FatalError.css";

interface FetchErrorProps {
  isOpen: boolean;
}

export default function FatalError({isOpen}: FetchErrorProps) {
  function returnToHome() {
    window.location.href = "/";
  }

  return (
    <Modal isOpen={ isOpen } onClose={ () => {
      returnToHome()
    } }>
      <div className={ "fatal-error-modal" }>
        <h2>Error</h2>
        <p>내 말 들려?</p>
        <p>지금 루미아 섬에 문제가 생겼나 봐.</p>
        <p>잠시 후에 다시 시도해봐.</p>
      </div>
    </Modal>
  );
}
