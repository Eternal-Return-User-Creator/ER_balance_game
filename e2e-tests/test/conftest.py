import os

from selenium import webdriver
from selenium.webdriver.chrome.options import Options as chromeOptions
from selenium.webdriver.firefox.options import Options as firefoxOptions
from selenium.webdriver.safari.options import Options as safariOptions
import pytest
from . import driver_factory

@pytest.fixture(scope="module")
def driver(request):
    browser = request.config.getoption("browser")
    if browser == "chrome":
        option = chromeOptions()
        option.add_argument("--headless")
        option.add_argument("--no-sandbox")
        option.add_argument("--disable-dev-shm-usage")
        option.add_argument(f"--user-data-dir=/tmp/chrome-user-data-{os.getpid()}")
        driver = webdriver.Chrome(options=option)
    elif browser == "firefox":
        option = firefoxOptions()
        option.add_argument("--headless")
        option.add_argument("--no-sandbox")
        option.add_argument("--disable-dev-shm-usage")
        driver = webdriver.Firefox(options=option)
    elif browser == "safari":
        driver = webdriver.Safari()
    else:
        raise ValueError(f"Unsupported browser: {browser}")
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
