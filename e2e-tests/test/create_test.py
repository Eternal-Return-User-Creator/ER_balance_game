import pytest
from selenium.webdriver.common.by import By
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.support import expected_conditions as ec


@pytest.fixture(scope='module')
def driver(driver):
    driver.get('http://localhost:3000/create')
    yield driver


def test_질문_생성_페이지_표시(driver):
    title = driver.find_element(By.CLASS_NAME, 'title')
    description = driver.find_element(By.CLASS_NAME, 'description')
    create_button = driver.find_element(By.CSS_SELECTOR, 'button.create-question')
    reset_button = driver.find_element(By.CLASS_NAME, 'reset')

    assert title.text == '질문 만들기'
    assert description.text == '질문을 작성하시는 걸 도와드릴게요!'
    assert create_button.text == '만들기'
    assert not reset_button.is_displayed()


@pytest.mark.usefixtures('driver')  # driver fixture를 사용하도록 명시
class Test_질문_생성:
    def test_질문_생성_성공(self, driver):
            driver.get('http://localhost:3000/create')
            # 질문 입력
            question_input = driver.find_element(By.CSS_SELECTOR, 'input.question')
            question_input.send_keys('테스트 질문')
            assert question_input.get_attribute('value') == '테스트 질문'

            # 선택지 입력
            choice_inputs = driver.find_elements(By.CSS_SELECTOR, 'input.choice')
            choice_inputs[0].send_keys('오메가')
            choice_inputs[1].send_keys('알파')
            assert choice_inputs[0].get_attribute('value') == '오메가'
            assert choice_inputs[1].get_attribute('value') == '알파'

    #     # 만들기 버튼 클릭
    #     create_button = driver.find_element(By.CSS_SELECTOR, 'button.create-question')
    #     create_button.click()
    #
    #     # 생성 중 메시지 확인
    #     description = driver.find_element(By.CLASS_NAME, 'description')
    #     assert description.text == '질문을 만들고 있어요.\n루미아 섬에 잘 전달되었으면...'
    #
    #     WebDriverWait(driver, 10).until(
    #         ec.text_to_be_present_in_element((
    #         By.CLASS_NAME, 'description'), '질문이 성공적으로 만들어졌어요!\n이제 게임을 하러 가거나, 질문을 더 만들어 볼까요?')
    #     )
    #
    #     # 초기화 버튼 클릭
    #     reset_button = driver.find_element(By.CLASS_NAME, 'reset')
    #     assert reset_button.is_displayed()
    #     reset_button.click()
    #
    #     # 입력 초기화 확인
    #     question_input = driver.find_element(By.CSS_SELECTOR, 'input.question')
    #     choice_inputs = driver.find_elements(By.CSS_SELECTOR, 'input.choice')
    #
    #     assert question_input.get_attribute('value') == ''
    #     assert choice_inputs[0].get_attribute('value') == ''
    #     assert choice_inputs[1].get_attribute('value') == ''
    #     assert not reset_button.is_displayed()

    class Test_질문_생성_실패:
        @pytest.mark.parametrize(
            "question, choices",
            [
                ('ㅆㅂ', ['선택지 A1', '선택지 B1']),
                ('테스트 질문 2', ['ㅆㅂ', '선택지 B2']),
                ('테스트 질문 3', ['선택지 A3', 'ㅆㅂ'])
            ]
        )
        def test_질문에_욕설_포함(self, driver, question, choices):
            driver.get('http://localhost:3000/create')
            question_input = driver.find_element(By.CSS_SELECTOR, 'input.question')
            question_input.send_keys(question)
            choice_inputs = driver.find_elements(By.CSS_SELECTOR, 'input.choice')
            [choice_input.send_keys(choice) for choice_input, choice in zip(choice_inputs, choices)]

            create_button = driver.find_element(By.CSS_SELECTOR, 'button.create-question')
            create_button.click()

            description = driver.find_element(By.CLASS_NAME, 'description')
            assert description.text == '질문을 만들고 있어요.\n루미아 섬에 잘 전달되었으면...'

            WebDriverWait(driver, 10).until(
                ec.text_to_be_present_in_element((
                    By.CLASS_NAME, 'description'), '욕설이나 무례한 말은 사용하실 수 없어요.\n다시 입력해주세요.')
            )

        def test_선택지_중복(self, driver):
            driver.get('http://localhost:3000/create')
            question_input = driver.find_element(By.CSS_SELECTOR, 'input.question')
            question_input.send_keys('테스트 질문 4')
            choice_inputs = driver.find_elements(By.CSS_SELECTOR, 'input.choice')
            [choice_input.send_keys('중복 선택지') for choice_input in choice_inputs]

            create_button = driver.find_element(By.CSS_SELECTOR, 'button.create-question')
            create_button.click()

            description = driver.find_element(By.CLASS_NAME, 'description')
            assert description.text == '질문을 만들고 있어요.\n루미아 섬에 잘 전달되었으면...'
            WebDriverWait(driver, 10).until(
                ec.text_to_be_present_in_element((
                    By.CLASS_NAME, 'description'), '선택지가 같아서는 안돼요.\n서로 다른 선택지를 입력해주세요.')
            )

        @pytest.mark.parametrize(
            "question, choices",
            [
                ('default question', ['choice A', 'choice B']),
                ('default question', ['choice B', 'choice A']),
            ]
        )
        def test_질문_중복(self, driver, question, choices):
            driver.get('http://localhost:3000/create')
            question_input = driver.find_element(By.CSS_SELECTOR, 'input.question')
            question_input.send_keys(question)
            choice_inputs = driver.find_elements(By.CSS_SELECTOR, 'input.choice')
            [choice_input.send_keys(choice) for choice_input, choice in zip(choice_inputs, choices)]

            create_button = driver.find_element(By.CSS_SELECTOR, 'button.create-question')
            create_button.click()

            description = driver.find_element(By.CLASS_NAME, 'description')
            assert description.text == '질문을 만들고 있어요.\n루미아 섬에 잘 전달되었으면...'

            WebDriverWait(driver, 10).until(
                ec.text_to_be_present_in_element((
                    By.CLASS_NAME, 'description'), '이미 등록된 질문이에요.\n다른 질문을 입력해주세요.')
            )
