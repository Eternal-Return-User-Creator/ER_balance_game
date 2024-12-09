import { useEffect, useState } from "react";
import "../assets/css/Game.css";

const backendURL = import.meta.env.VITE_BACKEND_URL;

export default function Game() {
  const [ question, setQuestion ] = useState("");
  const [ questionId, setQuestionId ] = useState(0);
  const [ choiceA, setChoiceA ] = useState("");
  const [ choiceB, setChoiceB ] = useState("");

  /**
   * 사용자가 선택지 A 또는 B를 클릭했을 때의 이벤트 핸들러입니다.
   * 선택지 A 또는 B를 클릭하면 선택지 A와 B의 버튼을 비활성화하고, 선택한 선택지의 버튼에 chosen 클래스를 추가합니다.
   * @param e - 클릭 이벤트
   */
  function handleClick(e: any) {
    e.preventDefault();
    const choiceAButton = document.querySelector(".choice[name='choiceA']") as HTMLButtonElement;
    const choiceBButton = document.querySelector(".choice[name='choiceB']") as HTMLButtonElement;
    // TODO => 백엔드 API 호출
    choiceAButton.disabled = true;
    choiceBButton.disabled = true;
    e.target.name === "choiceA" ? choiceAButton.classList.add("chosen") : choiceBButton.classList.add("chosen");
  }

  /**
   * 백엔드 API를 호출하여 다음 질문을 가져옵니다.
   */
  async function callGetQuestionAPI() {
    const response = await fetch(`${ backendURL }/question`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json"
      }
    });
    if (response.ok) {
      const data = await response.json();
      // TODO => questionId 어디에 사용할지 결정
      setQuestion(data.questionText);
      setQuestionId(data.id);
      setChoiceA(data.choiceA);
      setChoiceB(data.choiceB);
    }
    // TODO => 에러 처리
  }

  useEffect(() => {
    callGetQuestionAPI();
  }, []); // 빈 배열로 설정하여 한 번만 호출

  return (
    <div className={ "in-game content-wrapper" }>
      <div className={ "in-game question-wrapper" }>
        <p className={ "in-game question" }>{ question }</p>
      </div>
      <div className={ "in-game choice-wrapper" }>
        <form>
          <button className={ "in-game choice" } type={ "submit" } name={ "choiceA" } tabIndex={ 1 }
                  onClick={ handleClick }>A. { choiceA }</button>
          <button className={ "in-game choice" } type={ "submit" } name={ "choiceB" } tabIndex={ 2 }
                  onClick={ handleClick }>B. { choiceB }</button>
        </form>
      </div>
      <div className={ "next-button-wrapper" }>
        <button className={ "next-button" } tabIndex={ 3 }>&gt;&gt;</button>
      </div>
    </div>
  )
}
