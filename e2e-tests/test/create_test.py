import pytest


@pytest.fixture(scope='module')
def driver(driver):
    driver.get('http://localhost:3000/create')
    yield driver
