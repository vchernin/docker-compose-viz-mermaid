```mermaid
%%{init: {'theme': 'default'}}%%
flowchart TB
  P0((80)) -. 8080 .-> app
  P1((443)) -.-> app

  classDef ports fill:#f8f8f8,stroke:#ccc
  class P0,P1 ports
```