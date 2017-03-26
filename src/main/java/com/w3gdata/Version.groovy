package com.w3gdata

import com.w3gdata.util.ByteReader
import groovy.transform.Canonical
import groovy.transform.ToString


@Canonical
@ToString(includePackage = false)
class Version {
    public VersionIdentifiers versionIdentifier
    public int versionNumber
    public int buildNumber

    @Override
    String toString() {
        "$versionIdentifier 1.$versionNumber.$buildNumber"
    }

    enum VersionIdentifiers {
        WAR3("ROC"), W3XP("TFT")

        final String name

        VersionIdentifiers(String name) {
            this.name = name
        }

        @Override
        String toString() {
            return name
        }
    }

    Version(ByteReader reader) {
        versionIdentifier = reader.nextBytesAsString(4) as VersionIdentifiers
        versionNumber = reader.nextDWord()
        buildNumber = reader.nextWord()
    }

    Version(VersionIdentifiers versionIdentifier, int versionNumber, int buildNumber) {
        this.versionIdentifier = versionIdentifier
        this.versionNumber = versionNumber
        this.buildNumber = buildNumber
    }
}
