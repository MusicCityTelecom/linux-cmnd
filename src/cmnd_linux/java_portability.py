"""Narrow, hash-pinned Linux literal adaptations of private vendor classes.

No vendor class bytes are distributed. Constant-pool indices and every method's
bytecode stay unchanged; only the reviewed ASCII resource/command literals move.
"""
from hashlib import sha256
import struct
from zipfile import ZipFile


PATCHES = {
    'WEB-INF/classes/com/tpvision/smartinstall/util/ZipCommonUtils.class': (
        'f78e6480976ede073cc46c34a3c2ee7f59c4c5b56e431f2da475e6a418a826e6',
        {b'cmd /c 7z.exe a ': b'/usr/bin/cmnd-7zip a '}),
    'WEB-INF/classes/com/tpvision/smartinstall/androidapp/AndroidAppHelper.class': (
        '519528dd46f4dccbcfdcee96b449b015bd3905a297053c7d1ea5f37c39124f97',
        {b'bin\\keytool.exe': b'bin/keytool', b'\\keytool.exe': b'/keytool'}),
}


def adapt_literals(data: bytes, replacements: dict[bytes, bytes]) -> bytes:
    """Replace exactly one UTF8 constant per explicit key, never arbitrary bytes."""
    if len(data) < 10 or len(data) > 16 * 1024**2 or data[:4] != b'\xca\xfe\xba\xbe':
        raise ValueError('invalid Java class header/size')
    if not replacements or any(not old or not old.isascii() or not new.isascii()
                               or len(new) > 65535 for old, new in replacements.items()):
        raise ValueError('explicit bounded ASCII literal replacements required')
    count = struct.unpack_from('>H', data, 8)[0]
    offset, index, parts = 10, 1, [data[:10]]
    hits = dict.fromkeys(replacements, 0)
    while index < count:
        start = offset
        if offset >= len(data):
            raise ValueError('truncated Java constant pool')
        tag = data[offset]
        offset += 1
        if tag == 1:
            if offset + 2 > len(data):
                raise ValueError('truncated Java UTF8 length')
            size = struct.unpack_from('>H', data, offset)[0]
            offset += 2
            if offset + size > len(data):
                raise ValueError('truncated Java UTF8 value')
            value = data[offset:offset + size]
            offset += size
            if value in replacements:
                hits[value] += 1
                value = replacements[value]
            parts.append(b'\x01' + struct.pack('>H', len(value)) + value)
        else:
            size = {3:4, 4:4, 5:8, 6:8, 7:2, 8:2, 9:4, 10:4, 11:4, 12:4,
                    15:3, 16:2, 17:4, 18:4, 19:2, 20:2}.get(tag)
            if size is None or offset + size > len(data):
                raise ValueError('unknown/truncated Java constant')
            offset += size
            parts.append(data[start:offset])
            if tag in (5, 6):
                index += 1
        index += 1
    if index != count or len(data) - offset < 14 or any(n != 1 for n in hits.values()):
        raise ValueError('unexpected Java class layout or missing/duplicate reviewed literal')
    parts.append(data[offset:])
    return b''.join(parts)


def linux_class_replacements(source) -> dict[str, bytes]:
    result = {}
    with ZipFile(source) as archive:
        for name, (expected, replacements) in PATCHES.items():
            if archive.namelist().count(name) != 1:
                raise ValueError('missing/duplicate private vendor class for Linux adaptation')
            data = archive.read(name)
            if sha256(data).hexdigest() != expected:
                raise ValueError('unrecognized vendor class; Linux adaptation refused: ' + name)
            result[name] = adapt_literals(data, replacements)
    return result
