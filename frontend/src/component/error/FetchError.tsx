import Modal from "../modal/Modal.tsx";

interface FetchErrorProps {
  isOpen: boolean;
}

export default function FetchError({isOpen}: FetchErrorProps) {
  function returnToHome() {
    window.location.href = "/";
  }

  return (
    <Modal isOpen={ isOpen } onClose={ () => {
      returnToHome()
    } }>
      <div className={ "error-500 modal" }>
        <h2>500 Internal Server Error</h2>
        <p>서버에 문제가 발생했습니다. 잠시 후 다시 시도해주세요.</p>
      </div>
    </Modal>
  );
}
