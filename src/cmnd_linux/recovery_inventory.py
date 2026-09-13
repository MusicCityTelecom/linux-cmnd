"""Offline Java class identity inventory. Does not load or execute bytecode."""
from __future__ import annotations

from dataclasses import dataclass
import struct


@dataclass(frozen=True)
class ClassIdentity:
    name: str
    major: int


def class_identity(data: bytes) -> ClassIdentity:
    """Read this_class through the constant pool, independent of file extension.

    Bounds and name checks fail closed before names can be used as archive paths.
    This is an identity parser, not a verifier or proof of valid method bodies.
    """
    position = 0

    def take(size: int) -> bytes:
        nonlocal position
        if size < 0 or position + size > len(data):
            raise ValueError('truncated class identity')
        result = data[position:position + size]
        position += size
        return result

    def u2() -> int:
        return struct.unpack('>H', take(2))[0]

    if take(4) != b'\xca\xfe\xba\xbe':
        raise ValueError('not Java class bytecode')
    u2()  # minor
    major = u2()
    count = u2()
    pool: dict[int, tuple[int, bytes | int]] = {}
    index = 1
    sizes = {3: 4, 4: 4, 5: 8, 6: 8, 8: 2, 9: 4, 10: 4, 11: 4,
             12: 4, 15: 3, 16: 2, 17: 4, 18: 4, 19: 2, 20: 2}
    while index < count:
        tag = take(1)[0]
        if tag == 1:
            pool[index] = (tag, take(u2()))
        elif tag == 7:
            pool[index] = (tag, u2())
        elif tag in sizes:
            take(sizes[tag])
            if tag in (5, 6):
                index += 1
                if index >= count:
                    raise ValueError('invalid double-slot constant')
        else:
            raise ValueError('unsupported constant pool tag')
        index += 1
    u2()  # access flags
    class_index = u2()
    try:
        class_tag, name_index = pool[class_index]
        name_tag, encoded = pool[name_index]
        if class_tag != 7 or name_tag != 1 or not isinstance(encoded, bytes):
            raise ValueError('invalid class name reference')
        # Class names cannot contain NUL. JVM modified UTF-8 surrogate pairs are
        # decoded explicitly; other malformed encodings are retained as failures.
        name = encoded.decode('utf-8', errors='surrogatepass')
        name = name.encode('utf-16', errors='surrogatepass').decode('utf-16')
    except (KeyError, TypeError, UnicodeError) as error:
        raise ValueError('invalid class identity encoding/reference') from error
    parts = name.split('/')
    if not name or any(part in ('', '.', '..') for part in parts):
        raise ValueError('unsafe class name')
    if any(character in name for character in '\\:\x00[;') or any(ord(c) < 32 for c in name):
        raise ValueError('unsafe class name')
    return ClassIdentity(name=name, major=major)
