import { useEffect, useRef, useState } from "react";
import "../assets/css/Game.css";
import FatalError from "../component/error/FatalError.tsx";

const backendURL = import.meta.env.VITE_BACKEND_URL;

export default function Game() {
  const [ question, setQuestion ] = useState("");
  const questionIdRef = useRef<number>(0);
  const [ choiceA, setChoiceA ] = useState("");
  const [ choiceB, setChoiceB ] = useState("");
  const [ choiceARatio, setChoiceARatio ] = useState("");
  const [ choiceBRatio, setChoiceBRatio ] = useState("");
  const [ isDisabled, setIsDisabled ] = useState(false);
  const [ selectedChoice, setSelectedChoice ] = useState<string | null>(null);
  const [ isErrorModalOpen, setIsErrorModalOpen ] = useState(false);

  /**
   * 사용자가 선택지 A 또는 B를 클릭했을 때의 이벤트 핸들러입니다.
   * 선택지 A 또는 B를 클릭하면 선택지 A와 B의 버튼을 비활성화하고, 선택한 선택지의 버튼에 chosen 클래스를 추가합니다.
   * @param e - 클릭 이벤트
   */
  async function handleClick(e: any) {
    e.preventDefault();
    setIsDisabled(true);
    setSelectedChoice(e.target.name);
    await fetch(`${ backendURL }/question/${ questionIdRef.current }/choice-count?flag=${ e.target.name }`, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json"
      },
    }).then(async (response) => {
      if (response.ok) {
        // TODO => 결과를 확인하는 API를 호출합니다.
      } else {
        setIsErrorModalOpen(true);
      }
    }).catch(() => {
      setIsErrorModalOpen(true);
    });
  }

  /**
   * 백엔드 API를 호출하여 다음 질문을 가져옵니다.
   */
  async function callGetQuestionAPI() {
    await fetch(`${ backendURL }/question`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json"
      }
    }).then(async (response) => {
      if (response.ok) {
        const data = await response.json();
        setQuestion(data.questionText);
        questionIdRef.current = data.id;
        setChoiceA(data.choiceA);
        setChoiceB(data.choiceB);
      } else {
        setIsErrorModalOpen(true);
      }
    }).catch(() => {
      setIsErrorModalOpen(true);
    });
  }

  /**
   * 다음 질문을 가져오는 함수입니다.
   */
  async function getNextQuestion() {
    setIsDisabled(false);
    setSelectedChoice(null);
    await callGetQuestionAPI();
  }

  useEffect(() => {
    callGetQuestionAPI().then();
  }, []);

  return (
    <div className={ "in-game content-wrapper" }>
      <div className={ "in-game question-wrapper" }>
        <p className={ "in-game question" }>{ question }</p>
      </div>
      <div className={ "in-game choice-wrapper" }>
        <form className={"in-game"}>
          <button className={ `in-game choice ${ selectedChoice === 'A' ? 'chosen' : '' }` } type={ "submit" }
                  name={ "A" } tabIndex={ 1 }
                  onClick={ handleClick } disabled={ isDisabled }>
            <span className={"choice-text"}>A. { choiceA }</span>
            <span className={"choice-ratio"}>{choiceARatio}</span>
          </button>
          <button className={ `in-game choice ${ selectedChoice === 'B' ? 'chosen' : '' }` } type={ "submit" }
                  name={ "B" } tabIndex={ 2 }
                  onClick={ handleClick } disabled={ isDisabled }>
            <span className={"choice-text"}>B. { choiceB }</span>
            <span className={"choice-ratio"}>{choiceBRatio}</span>
          </button>
        </form>
      </div>
      <div className={ "next-button-wrapper" }>
        <button className={ "next-button" } tabIndex={ 3 } onClick={ getNextQuestion }>&gt;&gt;</button>
      </div>
      { isErrorModalOpen && <FatalError isOpen={ isErrorModalOpen }/> }
    </div>
  )
}
