import '../assets/css/Home.css'
import Banner from '../assets/images/banner.png'
import { useEffect, useState } from "react";

function Main() {
  const [ isAnimated, setIsAnimated ] = useState(false);

  useEffect(() => {
    // 컴포넌트가 로드되면 애니메이션 클래스 추가
    const timer = setTimeout(() => setIsAnimated(true), 0);
    return () => clearTimeout(timer);
  }, []);

  return (
    <div className={"home"}>
      <img src={ Banner } alt="배너 이미지" className={ `hidden ${ isAnimated ? `animate img-animation` : `` }` }/>
      <h1 className={ `hidden ${ isAnimated ? `animate title-animation` : `` }` }>이터널 리턴 밸런스 게임</h1>
      <button className={ `hidden ${ isAnimated ? `animate button-animation` : `` }` }>게임 시작</button>
    </div>
  );
}

export default Main
