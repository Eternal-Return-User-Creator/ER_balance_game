import Icon from "../assets/images/helper-icon.png"
import Default from "../assets/images/character/Charlotte/280. Prayge.png"
import "../assets/css/CreateQuestion.css"

export default function CreateQuestion() {
  return (
    <div className={ "content-wrapper" }>
      <div className={ "wrapper" }>
        <div className={ "helper-wrapper" }>
          <div className={ "icon-background" }>
            <img src={ Icon } alt={ "도움말 아이콘" } className={ "helper-icon" }/>
          </div>
          <div className={ "guide-section" }>
            <div className={ "speech-bubble" }>
              질문을 작성하시는 걸 도와드릴게요!
            </div>
            <div className={ "helper" }>
              <img src={ Default } alt={ "캐릭터 기본 이미지" } className={ "helper-img" }/>
            </div>
          </div>
        </div>
        <div className={ "question-wrapper" }>
          <form>
            <div className={ "input-wrapper" }>
              <p>질문</p>
              <input className={ "question" } type={ "text" } placeholder={ "질문을 입력해주세요" }/>
              <p>선택지 A</p>
              <input className={ "choice" } type={ "text" } placeholder={ "선택지를 입력해주세요" }/>
              <p>선택지 B</p>
              <input className={ "choice" } type={ "text" } placeholder={ "선택지를 입력해주세요" }/>
            </div>
            <div>
              <button type={ "submit" }>만들기</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  )
    ;
}
