```mermaid
%%{init: {'theme': 'default'}}%%
flowchart TB
  Vnamedvolume([named-volume]) x-. /nv .-x app
  Vhostbindro{{/host/bind-ro}} -. /bind/ro .-x app
  Vhostbindrw{{/host/bind-rw}} x-. /bind/rw .-x app
  V0{ } x-. /tmpfs .-x app
  Vhostnpipesock>/host/npipe.sock] x-. /npipe.sock .-x app

  classDef volumes fill:#fdfae4,stroke:#867a22
  class Vnamedvolume,Vhostbindro,Vhostbindrw,V0,Vhostnpipesock volumes
```