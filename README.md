# WolfyFunParser

EVERYTHING IS IN RUSSIAN/ВСЁ НА РУССКОМ:
Парсер K с популярного в СНГ сервера FunTime.

Этот плагин преобразует числа с суффиксом k в полноценные числовые значения. Например, 1k становится 1000, 10kk — 100000000 и так далее.

В конфигурационном файле вы можете указать команды следующим образом:

commands:
  - "/pay $arg"  # /pay ник AMOUNT

Пояснение: в команде /pay вторым аргументом идет ник игрока, поэтому мы используем $arg. После всех таких аргументов (включая $arg) плагин будет искать числа формата 1k, 10k и подобные и заменять их на числовые значения (1000, 10000 и т.д.).

Вы можете указать неограниченное количество $arg. Все они будут пропускаться, и замена будет применяться только к аргументам, следующим после них.



EVERYTHING IS IN ENGLISH/ВСЁ НА АНГЛИЙСКОМ:
K parser from the popular CIS server FunTime.

This plugin converts numbers with the suffix k into their full numeric form. For example, 1k becomes 1000, 10kk becomes 100000000, and so on.

In the configuration file, you can specify commands like this:

commands:
  - "/pay $arg"  # /pay playerName AMOUNT

Explanation: in the /pay command, the second argument is the player name, so we use $arg. After all such arguments (including $arg), the plugin searches for numbers like 1k, 10k, etc., and replaces them with their numeric equivalents (1000, 10000, etc.).

You can specify an unlimited number of $arg. All of them will be skipped, and the replacements will only apply to the parameters that come after them.
