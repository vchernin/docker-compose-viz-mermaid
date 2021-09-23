```mermaid
%%{init: {'theme': 'dark'}}%%
flowchart TB
  Vnamedvolume([named-volume]) x-. /nv .-x app
  Vhostbindro{{/host/bind-ro}} -. /bind/ro .-x app
  Vhostbindrw{{/host/bind-rw}} x-. /bind/rw .-x app
  V0{ } x-. /tmpfs .-x app
  Vhostnpipesock>/host/npipe.sock] x-. /npipe.sock .-x app

  classDef volumes fill:#0f544e,stroke:#23968b
  class Vnamedvolume,Vhostbindro,Vhostbindrw,V0,Vhostnpipesock volumes
```