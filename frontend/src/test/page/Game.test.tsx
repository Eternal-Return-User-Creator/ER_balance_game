import { describe, expect, vi } from "vitest";
import { cleanup, render, screen } from "@testing-library/react";
import Game from "../../page/Game.tsx";
import userEvent from "@testing-library/user-event";

const mockGetQuestionAPI = vi.fn();
const mockSelectChoiceAPI = vi.fn();
const mockGetChoiceResultAPI = vi.fn();

const mockGetQuestionResponse = {
  id: 1,
  questionText: "질문1",
  choiceA: "선택지 A1",
  choiceB: "선택지 B1",
  createdDate: "2024-12-11T04:17:45.752+00:00",
  updatedDate: "2024-12-11T04:17:45.752+00:00",
  choiceACount: 0,
  choiceBCount: 0
}

const mockNextQuestionResponse = {
  id: 2,
  questionText: "질문2",
  choiceA: "선택지 A2",
  choiceB: "선택지 B2",
  createdDate: "2024-12-11T04:17:45.752+00:00",
  updatedDate: "2024-12-11T04:17:45.752+00:00",
  choiceACount: 0,
  choiceBCount: 0
}

const mockGetChoiceResultResponse = {
  A: {count: "50", ratio: "50.01"},
  B: {count: "49", ratio: "49.99"}
}


describe("Game Page", () => {
  beforeEach(() => {
  });

  afterEach(() => {
    cleanup();
    mockGetQuestionAPI.mockReset();
    mockSelectChoiceAPI.mockReset();
    mockGetChoiceResultAPI.mockReset();
  });


  it("기본 상태 렌더링", async () => {
    mockGetQuestionAPI.mockResolvedValueOnce({
      ok: true,
      json: async () => (mockGetQuestionResponse),
    });

    render(<Game
      callGetQuestionAPI={ mockGetQuestionAPI }
      callSelectChoiceAPI={ mockSelectChoiceAPI }
      callGetChoiceResultAPI={ mockGetChoiceResultAPI }
    />);

    expect(await screen.findByText(new RegExp(mockGetQuestionResponse.questionText))).toBeInTheDocument();
    expect(screen.getByText(new RegExp(mockGetQuestionResponse.choiceA))).toBeInTheDocument();
    expect(screen.getByText(new RegExp(mockGetQuestionResponse.choiceB))).toBeInTheDocument();
    expect(mockGetQuestionAPI).toBeCalledTimes(1);
  });

  describe("선택지를 선택했을 때", () => {
    const setMockResponse = () => {
      mockGetQuestionAPI.mockResolvedValueOnce({
        ok: true,
        json: async () => (mockGetQuestionResponse),
      });
      mockSelectChoiceAPI.mockResolvedValueOnce({
        ok: true,
      });
      mockGetChoiceResultAPI.mockResolvedValueOnce({
        ok: true,
        json: async () => (mockGetChoiceResultResponse),
      });
    }

    const expectChoiceResult = async () => {
      expect(await screen.findByText(new RegExp(mockGetChoiceResultResponse.A.count))).toBeInTheDocument();
      expect(await screen.findByText(new RegExp(mockGetChoiceResultResponse.A.ratio))).toBeInTheDocument();
      expect(await screen.findByText(new RegExp(mockGetChoiceResultResponse.B.count))).toBeInTheDocument();
      expect(await screen.findByText(new RegExp(mockGetChoiceResultResponse.B.ratio))).toBeInTheDocument
      expect(mockSelectChoiceAPI).toBeCalledTimes(1);
      expect(mockGetChoiceResultAPI).toBeCalledTimes(1);
    }

    it("선택지 A를 선택", async () => {
      setMockResponse();
      render(<Game
        callGetQuestionAPI={ mockGetQuestionAPI }
        callSelectChoiceAPI={ mockSelectChoiceAPI }
        callGetChoiceResultAPI={ mockGetChoiceResultAPI }
      />);

      const choiceAButton = await screen.findByText(new RegExp(mockGetQuestionResponse.choiceA));
      userEvent.click(choiceAButton);

      await expectChoiceResult();
    });

    it("선택지 B를 선택", async () => {
      setMockResponse();
      render(<Game
        callGetQuestionAPI={ mockGetQuestionAPI }
        callSelectChoiceAPI={ mockSelectChoiceAPI }
        callGetChoiceResultAPI={ mockGetChoiceResultAPI }
      />);

      const choiceBButton = await screen.findByText(new RegExp(mockGetQuestionResponse.choiceB));
      userEvent.click(choiceBButton);

      await expectChoiceResult();
    });
  });

  it("다음 질문을 가져오는 버튼을 클릭했을 때", async () => {
    // 초기 질문 렌더링을 위한 API 응답 설정
    mockGetQuestionAPI.mockResolvedValueOnce({
      ok: true,
      json: async () => (mockGetQuestionResponse),
    });

    render(<Game
      callGetQuestionAPI={ mockGetQuestionAPI }
      callSelectChoiceAPI={ mockSelectChoiceAPI }
      callGetChoiceResultAPI={ mockGetChoiceResultAPI }
    />);

    // 다음 질문 렌더링을 위한 API 응답 설정
    mockGetQuestionAPI.mockResolvedValueOnce({
      ok: true,
      json: async () => (mockNextQuestionResponse),
    });
    const nextQuestionButton = screen.getByTestId("next-button");
    userEvent.click(nextQuestionButton);

    expect(await screen.findByText(new RegExp(mockNextQuestionResponse.questionText))).toBeInTheDocument();
    expect(screen.getByText(new RegExp(mockNextQuestionResponse.choiceA))).toBeInTheDocument();
    expect(screen.getByText(new RegExp(mockNextQuestionResponse.choiceB))).toBeInTheDocument();
    expect(mockGetQuestionAPI).toBeCalledTimes(2);
  });

  it("백엔드 API 호출 시 에러가 발생했을 때 에러 모달 노출", async () => {

  });
});
