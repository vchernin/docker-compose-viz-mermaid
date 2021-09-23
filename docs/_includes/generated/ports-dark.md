```mermaid
%%{init: {'theme': 'dark'}}%%
flowchart TB
  P0((80)) -. 8080 .-> app
  P1((443)) -.-> app

  classDef ports fill:#5a5757,stroke:#b6c2ff
  class P0,P1 ports
```