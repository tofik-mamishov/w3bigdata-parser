package com.w3gdata.parser

import jdk.nashorn.internal.ir.annotations.Immutable

@Immutable
class ReplayInformation {
    public ReplayHeader header
    public ReplaySubHeader subHeader

    ReplayInformation(ReplayHeader header, ReplaySubHeader subHeader) {
        this.header = header
        this.subHeader = subHeader
    }
}
