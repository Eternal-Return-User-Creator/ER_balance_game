import { useState } from "react";
import "../assets/css/Game.css";

export default function Game() {
  const [ question, setQuestion ] = useState("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam scelerisque tincidunt justo, non mi.");
  const [ choiceA, setChoiceA ] = useState("Lorem");
  const [ choiceB, setChoiceB ] = useState("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam scelerisque tincidunt justo, non mi.");

  function handleClick(e: any) {
    e.preventDefault();
    const choiceAButton = document.querySelector(".choice[name='choiceA']") as HTMLButtonElement;
    const choiceBButton = document.querySelector(".choice[name='choiceB']") as HTMLButtonElement;
    // TODO => 백엔드 API 호출
    choiceAButton.disabled = true;
    choiceBButton.disabled = true;
    e.target.name === "choiceA" ? choiceAButton.classList.add("chosen") : choiceBButton.classList.add("chosen");
  }

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
