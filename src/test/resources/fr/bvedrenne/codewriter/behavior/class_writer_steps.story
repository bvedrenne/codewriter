Scenario: When Writer have space class name then the creation fail

When I don't set a proper class name like (<name>)
Then I got an error

Examples:  
|name|
| |
|&é"|
|*/+|