import { renderToString } from "react-dom/server";
import { JSX } from "react";

/**
 * 숫자를 천 단위로 콤마(,)를 찍어 문자열로 반환합니다.
 * @param num - 콤마를 찍을 숫자
 */
export function formatNumberWithComma(num: number): string {
  return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
}

/**
 * JSX를 문자열로 변환합니다.
 * @param jsx - 변환할 JSX
 */
export function jsxToString(jsx: JSX.Element): string {
  const string = renderToString(jsx);
  return string.replace(/data-reactroot=""/g, "")
    .replace(/<br\s*\/?>/gi, '<br>')
    .replace(/&quot;/g, '"')
    .replace(/&nbsp;/g, ' ')
    .replace(/&amp;/g, '&');
}
