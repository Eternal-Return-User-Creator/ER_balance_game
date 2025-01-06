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
    assert reset_button.is_displayed() == False

def test_질문_생성(driver):
    # 질문을 입력한다.
    question_input = driver.find_element(By.CSS_SELECTOR, 'input.question')
    question_input.send_keys('테스트 질문')
    assert question_input.get_attribute('value') == '테스트 질문'

    # 선택지를 입력한다.
    choice_inputs = driver.find_elements(By.CSS_SELECTOR, 'input.choice')
    choice_inputs[0].send_keys('선택지 a')
    choice_inputs[1].send_keys('선택지 b')
    assert choice_inputs[0].get_attribute('value') == '선택지 a'
    assert choice_inputs[1].get_attribute('value') == '선택지 b'

    # 만들기 버튼을 클릭한다.
    create_button = driver.find_element(By.CSS_SELECTOR, 'button.create-question')
    create_button.click()

    # 질문을 생성 중임을 알리는 메시지가 표시된다.
    description = driver.find_element(By.CLASS_NAME, 'description')
    assert description.text == '질문을 만들고 있어요.\n루미아 섬에 잘 전달되었으면...'

    # 질문이 성공적으로 만들어졌음을 알리는 메시지가 표시된다.
    WebDriverWait(driver, 10).until(
        ec.text_to_be_present_in_element((By.CLASS_NAME, 'description'), '질문이 성공적으로 만들어졌어요!\n이제 게임을 하러 가거나, 질문을 더 만들어 볼까요?')
    )

    # 만들기 버튼이 사라지고 초기화 버튼이 표시된다.
    assert create_button.is_displayed() == False
    reset_button = driver.find_element(By.CLASS_NAME, 'reset')
    assert reset_button.is_displayed() == True
    reset_button.click()

    # 초기화 버튼을 클릭하면 입력한 내용이 초기화되고, 만들기 버튼이 다시 표시된다.
    assert question_input.get_attribute('value') == ''
    assert choice_inputs[0].get_attribute('value') == ''
    assert choice_inputs[1].get_attribute('value') == ''
    assert reset_button.is_displayed() == False
