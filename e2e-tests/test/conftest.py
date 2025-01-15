import pytest
from . import driver_factory

@pytest.fixture(scope="module")
def driver(request):
    browser = request.config.getoption("browser")
    driver = driver_factory(browser)
    yield driver
    driver.quit()

def pytest_addoption(parser):
    """pytest 옵션 추가"""
    parser.addoption(
        "--browser",
        action="store",
        default="chrome",
        help="브라우저 이름을 지정하세요. (chrome, firefox, safari)",
    )
