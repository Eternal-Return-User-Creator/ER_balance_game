/**
 * 질문을 생성하는 API를 호출합니다.
 */
const backendURL = import.meta.env.VITE_BACKEND_URL;

export async function postCreateQuestionAPI(question: string, choiceA: string, choiceB: string) {
  return await fetch(`${ backendURL }/question`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      questionText: question,
      choiceA: choiceA,
      choiceB: choiceB,
    }),
  });
}

/**
 * 질문을 가져오는 API를 호출합니다.
 */
export async function getQuestionAPI() {
  return await fetch(`${ backendURL }/question`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  });
}
