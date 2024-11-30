import Icon from "../assets/images/helper-icon.png"
import Default from "../assets/images/character/Charlotte/280. Prayge.png"
import "../assets/css/CreateQuestion.css"
import { useState } from "react";

export default function CreateQuestion() {
  const [ description, setDescription ] = useState(<>질문을 작성하시는 걸 도와드릴게요!</>);

  function describeQuestion() {
    setDescription(<>
        질문을 입력하는 곳이에요.<br/>
        "선호하는 것"이나 "더 싫은 것" 등을 물어보세요!<br/>
        아니면 "어떤 것이 더 좋은 선택일까요?"와 같은 질문도 좋아요.
      </>
    );
  }

  function describeChoice() {
    setDescription(<>
        선택지를 입력하는 곳이에요.<br/>
        질문에 대한 답변을 두 가지로 나눠주세요.
      </>
    );
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
                <img src={ Default } alt={ "캐릭터 기본 이미지" } className={ "helper-img" }/>
                <hr></hr>
                <p>Helper. 샬럿</p>
              </div>
            </div>
          </div>
          <div className={ "question-wrapper" }>
            <form>
              <div className={ "input-wrapper" }>
                <p>질문</p>
                <input className={ "question" } type={ "text" } placeholder={ "질문을 입력해주세요 (100자 이내)" } tabIndex={ 1 }
                       maxLength={ 100 } minLength={ 1 } onFocus={ describeQuestion }/>
                <p>선택지 A</p>
                <input className={ "choice" } type={ "text" } placeholder={ "선택지를 입력해주세요 (100자 이내)" } tabIndex={ 2 }
                       maxLength={ 100 } minLength={ 1 } onFocus={ describeChoice }/>
                <p>선택지 B</p>
                <input className={ "choice" } type={ "text" } placeholder={ "선택지를 입력해주세요 (100자 이내)" } tabIndex={ 3 }
                       maxLength={ 100 } minLength={ 1 } onFocus={ describeChoice }/>
              </div>
              <div>
                <button type={ "submit" } tabIndex={ 4 }>만들기</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}
