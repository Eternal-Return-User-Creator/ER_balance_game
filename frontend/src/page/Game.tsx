import React, { useEffect, useRef, useState } from "react";
import "../assets/css/Game.css";
import FatalError from "../component/error/FatalError.tsx";
import { formatNumberWithComma } from "../common/util/format.ts";
import { getChoiceResultAPI, getQuestionAPI, patchSelectChoiceAPI } from "../common/api/questionAPI.ts";

export default function Game(
  {
    callGetQuestionAPI = getQuestionAPI,
    callSelectChoiceAPI = patchSelectChoiceAPI,
    callGetChoiceResultAPI = getChoiceResultAPI
  }
) {
  const [ question, setQuestion ] = useState("");
  const questionIdRef = useRef<number>(0);
  const [ choiceA, setChoiceA ] = useState("");
  const [ choiceB, setChoiceB ] = useState("");
  const [ choiceAResult, setChoiceAResult ] = useState("");
  const [ choiceBResult, setChoiceBResult ] = useState("");
  const [ isDisabled, setIsDisabled ] = useState(false);
  const [ selectedChoice, setSelectedChoice ] = useState<string | null>(null);
  const [ isErrorModalOpen, setIsErrorModalOpen ] = useState(false);

  /**
   * 사용자가 선택지 A 또는 B를 클릭했을 때의 이벤트 핸들러입니다.
   * 선택지 A 또는 B를 클릭하면 선택지 A와 B의 버튼을 비활성화하고, 선택한 선택지의 버튼에 chosen 클래스를 추가합니다.
   * @param e - 클릭 이벤트
   */
  async function handleClick(e: React.MouseEvent<HTMLButtonElement>) {
    const flag: string = e.currentTarget.name;

    e.preventDefault();
    setIsDisabled(true);
    setSelectedChoice(flag);

    try {
      const response = await callSelectChoiceAPI(questionIdRef.current, flag);
      if (response.ok) {
        await getChoiceResult();
      } else {
        setIsErrorModalOpen(true);
      }
    } catch {
      setIsErrorModalOpen(true);
    }
  }

  /**
   * 백엔드 API를 호출하여 선택지 A와 B의 비율을 가져옵니다.
   */
  async function getChoiceResult() {
    try {
      const response = await callGetChoiceResultAPI(questionIdRef.current);
      if (response.ok) {
        const data = await response.json();
        const aResult = data.A;
        const bResult = data.B;
        setChoiceAResult(`${ formatNumberWithComma(aResult.count) } (${ aResult.ratio }%)`);
        setChoiceBResult(`${ formatNumberWithComma(bResult.count) } (${ bResult.ratio }%)`);
      } else {
        setIsErrorModalOpen(true);
      }
    } catch {
      setIsErrorModalOpen(true);
    }
  }

  /**
   * 백엔드 API를 호출하여 다음 질문을 가져옵니다.
   */
  async function getQuestion() {
    try {
      const response = await callGetQuestionAPI();
      if (response.ok) {
        const data = await response.json();
        setQuestion(data.questionText);
        questionIdRef.current = data.id;
        setChoiceA(data.choiceA);
        setChoiceB(data.choiceB);
      } else {
        setIsErrorModalOpen(true);
      }
    } catch {
      setIsErrorModalOpen(true);
    }
  }

  /**
   * 다음 질문을 가져오는 함수입니다.
   */
  async function getNextQuestion() {
    setIsDisabled(false);
    setSelectedChoice(null);
    setChoiceAResult("");
    setChoiceBResult("");
    await getQuestion();
  }

  useEffect(() => {
    getQuestion().then();
  }, []);

  return (
    <div className={ "in-game content-wrapper" }>
      <div className={ "in-game question-wrapper" }>
        <p className={ "in-game question" } data-testid={"question"}>{ question }</p>
      </div>
      <div className={ "in-game choice-wrapper" }>
        <form className={ "in-game" }>
          <button className={ `in-game choice ${ selectedChoice === 'A' ? 'chosen' : '' }` } type={ "submit" }
                  name={ "A" } tabIndex={ 1 }
                  onClick={ handleClick } disabled={ isDisabled }>
            <span className={ "choice-text" } data-testid={"choice-a"}>A. { choiceA }</span>
            <span className={ "choice-ratio" } data-testid={"choice-a-ratio"}>{ choiceAResult }</span>
          </button>
          <button className={ `in-game choice ${ selectedChoice === 'B' ? 'chosen' : '' }` } type={ "submit" }
                  name={ "B" } tabIndex={ 2 }
                  onClick={ handleClick } disabled={ isDisabled }>
            <span className={ "choice-text" } data-testid={"choice-b"}>B. { choiceB }</span>
            <span className={ "choice-ratio" } data-testid={"choice-b-ratio"}>{ choiceBResult }</span>
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
