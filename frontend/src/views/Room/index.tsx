import {useGetRoomStateQuery} from "../../store/types.generated";
import React, {useState} from "react";
import {ClosedRoom} from "./ClosedRoom";
import {NewRoom} from "./NewRoom";
import {StartedRoom} from "./StartedRoom";

export function RoomPage() {
    const {data: room} = useGetRoomStateQuery({roomId: 0})
    if (room?.isClosed) return <ClosedRoom/>
    if (!room?.isStarted) return <NewRoom/>
    return <StartedRoom></StartedRoom>
}