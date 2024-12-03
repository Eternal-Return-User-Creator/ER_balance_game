import Icon from "../assets/images/helper-icon.png"
import Default from "../assets/images/character/Charlotte/280. Prayge.png"
import Error from "../assets/images/character/Charlotte/278. No Way!.png"
import Fail from "../assets/images/character/Charlotte/279. Heartbroken.png"
import Success from "../assets/images/character/Charlotte/277. A Pure Heart.png"
import "../assets/css/CreateQuestion.css"
import { useState } from "react";
import { charlotteMessages, charlotteErrorMessages } from "../common/message/charlotteMessage.tsx";
import { localBackendURL } from "../common/constants.ts";

export default function CreateQuestion() {
  const [ description, setDescription ] = useState(charlotteMessages.defaultDescription);
  const [ image, setImage ] = useState(Default);
  const [ question, setQuestion ] = useState("");
  const [ choiceA, setChoiceA ] = useState("");
  const [ choiceB, setChoiceB ] = useState("");

  function describeQuestion() {
    setDescription(charlotteMessages.questionDescription);
    setImage(Default);
  }

  function describeChoice() {
    setDescription(charlotteMessages.choiceDescription);
    setImage(Default);
  }

  /**
   * 질문과 선택지 A, B의 입력값을 검증합니다.
   * 입력값이 없을 경우, 해당 입력란에 포커스를 맞추고 에러 메시지를 출력합니다.
   * @param event - form submit event
   * @returns {boolean} - 입력값이 유효한 경우 true, 그렇지 않은 경우 false
   */
  function validateContent(event: any) {
    event.preventDefault();
    if (question === "") {
      const questionInput = document.querySelector(".question") as HTMLInputElement;
      questionInput.focus();
      setDescription(charlotteErrorMessages.emptyInputQuestion);
      setImage(Error);
      return false;
    }
    if (choiceA === "" || choiceB === "") {
      const choiceInputs = document.querySelectorAll(".choice");
      choiceA === "" ? (choiceInputs[0] as HTMLInputElement).focus() : (choiceInputs[1] as HTMLInputElement).focus();
      setDescription(charlotteErrorMessages.emptyInputChoice);
      setImage(Error);
      return false;
    }
    return true;
  }

  async function callCreateQuestionAPI() {
    setDescription(charlotteMessages.createDescription);
    setImage(Default);
    const response = await fetch(`${localBackendURL}/question`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        questionText: question,
        choiceA: choiceA,
        choiceB: choiceB,
      }),
    });
    if (response.ok) {
      setDescription(charlotteMessages.createSuccessDescription);
      setImage(Success);
    }
  }

  async function handleSubmit(event: any) {
    if (!validateContent(event)) {
      return;
    }
    await callCreateQuestionAPI();
  }

  return (
    <div className={ "content-wrapper" }>
      <div className={ "wrapper" }>
        <div className={ "wrapper-pattern" }>
          <div className={ "helper-wrapper" }>
            <div className={ "icon-background" }>
              <img src={ Icon } alt={ "도움말 아이콘" } className={ "helper-icon" }/>
            </div>
            <div className={ "guide-section" }>
              <div className={ "speech-bubble" }>
                <p className={ "title" }>질문 만들기</p>
                <p className={ "description" }>{ description }</p>
              </div>
              <div className={ "helper" }>
                <img src={ image } alt={ "캐릭터 기본 이미지" } className={ "helper-img" }/>
                <hr></hr>
                <p>Helper. 샬럿</p>
              </div>
            </div>
          </div>
          <div className={ "question-wrapper" }>
            <form onSubmit={ handleSubmit }>
              <div className={ "input-wrapper" }>
                <p>질문</p>
                <input className={ "question" } type={ "text" } placeholder={ "질문을 입력해주세요 (100자 이내)" } tabIndex={ 1 }
                       maxLength={ 100 } onFocus={ describeQuestion } value={ question } onChange={ (e) => {
                  setQuestion(e.target.value)
                } }/>
                <p>선택지 A</p>
                <input className={ "choice" } type={ "text" } placeholder={ "선택지를 입력해주세요 (100자 이내)" } tabIndex={ 2 }
                       maxLength={ 100 } onFocus={ describeChoice } value={ choiceA } onChange={ (e) => {
                  setChoiceA(e.target.value)
                } }/>
                <p>선택지 B</p>
                <input className={ "choice" } type={ "text" } placeholder={ "선택지를 입력해주세요 (100자 이내)" } tabIndex={ 3 }
                       maxLength={ 100 } onFocus={ describeChoice } value={ choiceB } onChange={ (e) => {
                  setChoiceB(e.target.value)
                } }/>
              </div>
              <div>
                <button className={ "create-question" } type={ "submit" } tabIndex={ 4 }>만들기</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}
