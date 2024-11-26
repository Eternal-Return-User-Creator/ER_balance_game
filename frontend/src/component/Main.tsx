import '../assets/css/Main.css'
import Banner from '../assets/images/banner.png'

function Main() {
  return (
    <main>
      <img src={Banner} alt="배너 이미지" />
      <h1>이터널 리턴 밸런스 게임</h1>
      <button>게임 시작</button>
    </main>
  )
}

export default Main
