import { Link } from "react-router-dom";

export default function Header() {
  return (
    <header>
      <h1>ER</h1>
      <h1>-BAL</h1>
      <nav>
        <ul>
          <li>
            <Link to="/">처음으로</Link>
          </li>
          <li>
            <a href={'#'}>게임시작</a>
          </li>
          <li>
            <Link to="/create">만들기</Link>
          </li>
        </ul>
      </nav>
    </header>
  )
}
