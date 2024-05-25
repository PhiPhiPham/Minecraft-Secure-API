[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-718a45dd9cf7e7f842a935f5ebbe5719a5e09af4491e668f4dbf3b35d5cca122.svg)](https://classroom.github.com/online_ide?assignment_repo_id=11232751&assignment_repo_type=AssignmentRepo)
# Minecraft Secure API
This is an implementation with a team of 3 for the Minecraft Random Village Generator Project to see if we could securely encrypt the messages when sent from the API to Minecraft. 
This is the research that was done to ensure that we made a secure encryption:
[https://docs.google.com/document/d/1QC6-hrXeQ6aUv4C5SY2KSTm1pUbmmvNYxUw9z3_gKPY/edit?usp=sharing](https://docs.google.com/document/d/1ItxWfDyCQFVB5R0AZ92IkamtE6A2RxzhkeKB2DoFW8k/edit)

### IMPLEMENTATION ###
- /ELCI/ folder of this git contains the modified Java code for our secured ELCI plugin.
  - pom.xml was modified to add certain dependencies. 
  - Maven should automatically add these, but we have provided a compiled JAR with dependencies (ELCI-1.12.1-SECURE.jar).
- /mcpi/ folder of this git contains the modified Python code for our secured MCPI library.
  - This requires the cryptography package to be installed. 
- generate_keys.py is the code used to generate our two keys, enc.key and mac.key. 
- mcpi_sec.py is the code used to demonstrate the security vulnerability/fix in the video.


