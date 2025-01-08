import {useGetRoomStateQuery} from "../../store/types.generated";
import {ClosedRoom} from "./ClosedRoom";
import {NewRoom} from "./NewRoom";
import {RunningRoom} from "./RunningRoom";

export function RoomPage() {
    const {data: room} = useGetRoomStateQuery({roomId: 0})
    if (room?.isClosed) return <ClosedRoom/>
    if (!room?.isStarted) return <NewRoom/>
    return <RunningRoom/>
}