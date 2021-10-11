def max_product(numbers):
    numbers.sort(reverse=True)
    return numbers[0] * numbers[1] * numbers[2]
