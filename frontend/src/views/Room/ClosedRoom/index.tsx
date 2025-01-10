import {useGetApiMeQuery, useGetRoomStateQuery} from "../../../store/types.generated";
import {useEffect} from "react";
import {BunkerInformation} from "../../../components/BunkerInformation";
import {CharactersTable} from "../../../components/CharactersTable";
import {Link, useParams} from "react-router-dom";
import {PollInformation} from "../../../components/PollInformation";

export function ClosedRoom() {
    const {roomId: roomIdStr} = useParams<{ roomId: string }>()
    const roomId = Number(roomIdStr)
    const {data: roomData, refetch: refetchRoomData} = useGetRoomStateQuery({roomId: roomId})
    const {data: user} = useGetApiMeQuery()

    useEffect(() => {
        const intervalId = setInterval(refetchRoomData, 200)
        return () => clearInterval(intervalId)
    }, [refetchRoomData])

    return (
        <div className="min-h-screen bg-burgundy-900 text-white p-8">
            <header className="text-center mb-8">
                <h1 className="text-2xl font-bold text-burgundy-300 mb-4">ИГРА ОКОНЧЕНА</h1>
                <Link to={"/main"}>Вернуться на главную</Link>
            </header>
            <section className="mb-12">
                <BunkerInformation roomData={roomData}/>
            </section>
            {user && <>
                <section>
                    <CharactersTable roomData={roomData} canOpen={false} user={user}/>
                </section>
                <section>
                    <PollInformation roomData={roomData} canOpen={false} user={user}/>
                </section>
            </>
            }
        </div>
    );
}