export default function Game() {
  return (
    <div className={"content-wrapper"}>
      <div className={"question-wrapper"}>
        <p className={"question"}></p>
      </div>
      <div className={"choice-wrapper"}>
        <form>
          <button className={"choice"} type={"submit"} name={"choiceA"}></button>
          <button className={"choice"} type={"submit"} name={"choiceB"}></button>
        </form>
      </div>
      <div className={ "next-button-wrapper" }>
        <button className={ "next-button" }>&gt;&gt;</button>
      </div>
    </div>
  )
}
