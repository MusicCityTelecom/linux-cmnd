import sys


# Keep the first 0.8.0 coexistence probe isolated from the qualified 0.7.1 CLI
# dispatcher. The Debian cmndctl wrapper invokes `python3 -m cmnd_linux`, so
# this adds `cmndctl environment` without changing existing command behavior.
if sys.argv[1:] == ['environment']:
    from .environment import main as environment_main

    raise SystemExit(environment_main())

from .cli import main

raise SystemExit(main())
