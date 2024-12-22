import { describe, it } from "@jest/globals";
import { render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import CreateQuestion from "../../page/CreateQuestion.tsx";

describe("CreateQuestion Page", () => {
  it('컴포넌트 렌더링 확인', () => {
    render(<CreateQuestion/>);
    expect(screen.getByText('질문 만들기')).toBeInTheDocument();
    expect(screen.getByPlaceholderText('질문을 입력해주세요 (100자 이내)')).toBeInTheDocument();
    screen.getAllByPlaceholderText('선택지를 입력해주세요 (100자 이내)').forEach((element) => {
      expect(element).toBeInTheDocument();
    });
    expect(screen.getByText('만들기')).toBeInTheDocument();
    expect(screen.getByText('다시 작성')).toBeInTheDocument();
  });
});
