import sys


# Keep the first 0.8.0 coexistence probes isolated from the qualified 0.7.1 CLI
# dispatcher. The Debian cmndctl wrapper invokes `python3 -m cmnd_linux`, so
# these read-only commands do not change existing command behavior.
if sys.argv[1:] == ['environment']:
    from .environment import main as environment_main

    raise SystemExit(environment_main())
if sys.argv[1:] == ['plan-install']:
    from .install_plan import main as plan_main

    raise SystemExit(plan_main())

from .cli import main

raise SystemExit(main())
