import { useState } from "react";
import "../assets/css/Game.css";

export default function Game() {
  const [ question, setQuestion ] = useState("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam scelerisque tincidunt justo, non mi.");
  const [ choiceA, setChoiceA ] = useState("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam scelerisque tincidunt justo, non mi.");
  const [ choiceB, setChoiceB ] = useState("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam scelerisque tincidunt justo, non mi.");

  return (
    <div className={ "content-wrapper" }>
      <div className={ "question-wrapper" }>
        <p className={ "question" }>{ question }</p>
      </div>
      <div className={ "choice-wrapper" }>
        <form>
          <button className={ "choice" } type={ "submit" } name={ "choiceA" } tabIndex={ 1 }>A. { choiceA }</button>
          <button className={ "choice" } type={ "submit" } name={ "choiceB" } tabIndex={ 2 }>B. { choiceB }</button>
        </form>
      </div>
      <div className={ "next-button-wrapper" }>
        <button className={ "next-button" } tabIndex={ 3 }>&gt;&gt;</button>
      </div>
    </div>
  )
}
