import Icon from "../assets/images/helper-icon.png"
import Default from "../assets/images/character/Charlotte/280. Prayge.png"
import "../assets/css/CreateQuestion.css"

export default function CreateQuestion() {
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
                <p className={"title"}>질문 만들기</p>
                <p className={"description"}>질문을 작성하시는 걸 도와드릴게요!</p>
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
                <input className={ "question" } type={ "text" } placeholder={ "질문을 입력해주세요" } tabIndex={1}/>
                <p>선택지 A</p>
                <input className={ "choice" } type={ "text" } placeholder={ "선택지를 입력해주세요" } tabIndex={2}/>
                <p>선택지 B</p>
                <input className={ "choice" } type={ "text" } placeholder={ "선택지를 입력해주세요" } tabIndex={3}/>
              </div>
              <div>
                <button type={ "submit" } tabIndex={4}>만들기</button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  )
    ;
}
