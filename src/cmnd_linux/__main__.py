import sys

args = sys.argv[1:]

if args == ['environment']:
    from .environment import main as command
    raise SystemExit(command())
if args == ['plan-install']:
    from .install_plan import main as command
    raise SystemExit(command())
if args and args[0] == 'apt-activate':
    from .apt_installer import main as command
    raise SystemExit(command(args[1:]))
if args == ['shared-wait-database']:
    from .shared_database import wait_shared_database
    wait_shared_database()
    raise SystemExit(0)
if args and args[0] == 'apache-detach':
    from .apache_integration import detach
    import json
    execute = args[1:] == ['--execute']
    if args[1:] not in ([], ['--execute']):
        raise SystemExit('Usage: cmndctl apache-detach [--execute]')
    print(json.dumps(detach(execute=execute), indent=2, sort_keys=True))
    raise SystemExit(0)
if args and args[0] == 'docker-render':
    from .docker_bundle import main as command
    raise SystemExit(command(args[1:]))

from .cli import main
raise SystemExit(main())
