import { Link } from "react-router-dom";

export default function Header() {
  return (
    <header>
      <h1>ER</h1>
      <h1>-BAL</h1>
      <nav>
        <ul>
          <li>
            <Link to="/" id={"back-to-main-link"}>처음으로</Link>
          </li>
          <li>
            <Link to="/game" id={"game-start-link"}>게임시작</Link>
          </li>
          <li>
            <Link to="/create" id={"create-question-link"}>만들기</Link>
          </li>
        </ul>
      </nav>
    </header>
  )
}
