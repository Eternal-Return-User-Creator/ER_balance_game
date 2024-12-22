import { describe, it, expect } from "vitest";
import { act, render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import CreateQuestion from "../../page/CreateQuestion.tsx";
import { charlotteErrorMessages, charlotteMessages } from "../../common/message/charlotteMessage.tsx";
import userEvent from "@testing-library/user-event";
import { jsxToString } from "../../common/util/format.ts";

describe("CreateQuestion Page", () => {
  beforeEach(async () => {
    await act(async () => {
      render(<CreateQuestion/>);
    });
  });
  describe("렌더링 확인", () => {
    it('컴포넌트 렌더링 확인', () => {
      expect(screen.getByText('질문 만들기')).toBeInTheDocument();
      expect(screen.getByPlaceholderText('질문을 입력해주세요 (100자 이내)')).toBeInTheDocument();
      screen.getAllByPlaceholderText('선택지를 입력해주세요 (100자 이내)').forEach((element) => {
        expect(element).toBeInTheDocument();
      });
      expect(screen.getByText('만들기')).toBeInTheDocument();
      expect(screen.getByText('다시 작성')).toBeInTheDocument();
    });

    it('질문 입력란 확인', () => {
      const questionInput = screen.getByPlaceholderText('질문을 입력해주세요 (100자 이내)');
      expect(questionInput).toBeInTheDocument();
      expect(questionInput).toHaveAttribute('maxlength', '100');
    });

    it('선택지 입력란 확인', () => {
      screen.getAllByPlaceholderText('선택지를 입력해주세요 (100자 이내)').forEach((element) => {
        expect(element).toBeInTheDocument();
        expect(element).toHaveAttribute('maxlength', '100');
      });
    });

    it('만들기 버튼 확인', () => {
      const submitButton = screen.getByText('만들기');
      expect(submitButton).toBeInTheDocument();
      expect(submitButton).toBeEnabled();
    });

    it('다시 작성 버튼 확인', () => {
      const resetButton = screen.getByText('다시 작성');
      expect(resetButton).toBeInTheDocument();
      expect(resetButton).not.toBeVisible();
    });
  })

  describe("포커스 확인", () => {
    it('질문 입력란 포커스 확인', () => {
      const questionInput = screen.getByPlaceholderText('질문을 입력해주세요 (100자 이내)');
      expect(questionInput).not.toHaveFocus();
    });

    it('선택지 입력란 포커스 확인', () => {
      const choiceInputs = screen.getAllByPlaceholderText('선택지를 입력해주세요 (100자 이내)');
      choiceInputs.forEach((element) => {
        expect(element).not.toHaveFocus();
      });
    });

    it('질문 입력란 포커스 이동 확인', () => {
      const questionInput = screen.getByPlaceholderText('질문을 입력해주세요 (100자 이내)');
      act(() => {
        questionInput.focus();
      });
      expect(questionInput).toHaveFocus();
    });

    it('선택지 입력란 포커스 이동 확인', () => {
      const choiceInputs = screen.getAllByPlaceholderText('선택지를 입력해주세요 (100자 이내)');
      act(() => {
        choiceInputs.forEach((element) => {
          element.focus();
          expect(element).toHaveFocus();
        });
      });
    });
  });

  describe("질문 입력 후 프론트엔드 유효성 검사", () => {
    it('질문 입력란 에러 메시지 확인', async () => {
      const submitButton = screen.getByText('만들기');
      const description = screen.getByTestId('description');

      await act(async () => {
        await userEvent.click(submitButton);
      });

      const errorMessages = jsxToString(charlotteErrorMessages.emptyInputQuestion);
      expect(description.innerHTML).toContain(errorMessages);
    });

    it('선택지 입력란 에러 메시지 확인', async () => {
      const submitButton = screen.getByText('만들기');
      const questionInput = screen.getByPlaceholderText('질문을 입력해주세요 (100자 이내)');
      const description = screen.getByTestId('description');

      await act(async () => {
        await userEvent.type(questionInput, '질문');
        await userEvent.click(submitButton);
      });

      const errorMessages = jsxToString(charlotteErrorMessages.emptyInputChoice);
      expect(description.innerHTML).toContain(errorMessages);
    });

    it('질문 입력란 에러 메시지 확인 후 포커스 이동', async () => {
      const submitButton = screen.getByText('만들기');

      await act(async () => {
        await userEvent.click(submitButton);
      });
      const questionInput = screen.getByPlaceholderText('질문을 입력해주세요 (100자 이내)');
      expect(questionInput).toHaveFocus();
    });

    it('선택지 입력란 에러 메시지 확인 후 포커스 이동', async () => {
      const submitButton = screen.getByText('만들기');
      const questionInput = screen.getByPlaceholderText('질문을 입력해주세요 (100자 이내)');

      await act(async () => {
        await userEvent.type(questionInput, '질문');
        await userEvent.click(submitButton);
      });

      const choiceInputs = screen.getAllByPlaceholderText('선택지를 입력해주세요 (100자 이내)');
      expect(choiceInputs[0]).toHaveFocus();
    });
  });

  describe("질문 생성 중 기능 테스트", () => {
    it ("질문 생성 중 설명 텍스트 검사", async () => {
      const submitButton = screen.getByText('만들기');
      const questionInput = screen.getByPlaceholderText('질문을 입력해주세요 (100자 이내)');
      const choiceInputs = screen.getAllByPlaceholderText('선택지를 입력해주세요 (100자 이내)');
      const description = screen.getByTestId('description');

      await act(async () => {
        await userEvent.type(questionInput, '질문');
        await userEvent.type(choiceInputs[0], '선택지 A');
        await userEvent.type(choiceInputs[1], '선택지 B');
        await userEvent.click(submitButton);
      });
      const responseMessage = jsxToString(charlotteMessages.createDescription);

      expect(description.innerHTML).toContain(responseMessage);
    });
  });

  describe("질문 생성 테스트", () => {
    it("질문 생성 성공", () => {

    });

    describe("질문 생성 실패", () => {
      it("욕설 입력", () => {

      });

      it("같은 선택지 입력", () => {

      });

      it("이미 등록된 질문", () => {

      });

      it("서버 에러", () => {

      });
    });
  });
});
