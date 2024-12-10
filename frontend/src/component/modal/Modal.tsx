import React from "react";
import ReactDOM from "react-dom";
import "../../assets/css/Modal.css";

/**
 * 모달 컴포넌트입니다.
 * @param isOpen - 모달이 열려있는지 여부
 * @param onClose - 모달을 닫을 때 호출할 함수
 * @param children - 모달 내부에 표시할 JSX
 * @constructor
 */
export default function Modal({isOpen, onClose, children}: {
  isOpen: boolean,
  onClose: () => void,
  children: React.ReactNode
}) {
  if (!isOpen) return null;

  return ReactDOM.createPortal(
    <div className="modal-overlay" onClick={ onClose }>
      <div
        className="modal-content"
        onClick={ (e) => e.stopPropagation() } // 클릭 이벤트 전파 방지
      >
        { children }
      </div>
    </div>,
    document.body // 포탈의 대상 DOM 노드
  );
}
