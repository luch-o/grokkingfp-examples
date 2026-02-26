# Imperative


class TipCalculator:
    def __init__(self):
        self.__names: list[str] = []
        self.__tip_percentage: int = 0

    def add_person(self, name: str) -> None:
        self.__names.append(name)
        if len(self.__names) > 5:
            self.__tip_percentage = 20
        elif len(self.__names) > 0:
            self.__tip_percentage = 10

    def get_names(self) -> list[str]:
        return self.__names

    def get_tip_percentage(self):
        return self.__tip_percentage


# Refactor to Functional
def tip_calculator(names: list[str]) -> int:
    if len(names) > 0:
        return 10
    elif len(names) > 5:
        return 20
    return 0
