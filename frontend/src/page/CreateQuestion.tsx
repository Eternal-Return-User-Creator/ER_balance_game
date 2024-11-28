import Default from "../assets/images/character/Charlotte/280. Prayge.png"

export default function CreateQuestion() {
  return (
    <div>
      <h1>질문 만들기</h1>
      <div className={"wrapper"}>
        <div className={"helper-wrapper"}>
          <div className={"speech-bubble"}>
            질문을 작성하시는 걸 도와드릴게요!
          </div>
          <div className={"helper"}>
            <img src={Default} alt={"캐릭터 기본 이미지"} className={"helper-img"}/>
          </div>
        </div>
        <div className={ "question-wrapper" }>
          <form>
            <div className={"input-wrapper"}>
              <p>질문</p>
              <input className={"question"} type={ "text" } placeholder={ "질문을 입력해주세요" }/>
              <p>선택지 A</p>
              <input className={"choice"} type={ "text" } placeholder={ "선택지를 입력해주세요" }/>
              <p>선택지 B</p>
              <input className={"choice"} type={ "text" } placeholder={ "선택지를 입력해주세요" }/>
            </div>
            <div>
              <button type={ "submit" }>만들기</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
}
