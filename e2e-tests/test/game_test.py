import pytest


@pytest.fixture(scope="module")
def driver(driver):
    driver.get("http://localhost:3000/game")
    yield driver

def test_게임_페이지_표시(driver):
    pass

def test_선택지_클릭_시_결과_표시(driver):
    pass

def test_다음_질문_버튼_클릭_시_다음_질문_표시(driver):
    pass