import {useGetApiMeQuery, useGetRoomStateQuery} from "../../../store/types.generated";
import {useEffect} from "react";
import {BunkerInformation} from "../../../components/BunkerInformation";
import {CharactersTable} from "../../../components/CharactersTable";
import {Link, useParams} from "react-router-dom";
import {PollInformation} from "../../../components/PollInformation";

export function RunningRoom() {
    const {roomId: roomIdStr} = useParams<{ roomId: string }>()
    const roomId = Number(roomIdStr)
    const {data: user} = useGetApiMeQuery()
    const {data: roomData, refetch: refetchRoomData} = useGetRoomStateQuery({roomId: roomId})

    useEffect(() => {
        const intervalId = setInterval(refetchRoomData, 200)
        return () => clearInterval(intervalId)
    }, [refetchRoomData])

    return (
        <div className="min-h-screen bg-burgundy-900 text-white p-8">
            <header className="text-center mb-8">
                <h1 className="text-2xl font-bold text-burgundy-300 mb-4">ДОБРО ПОЖАЛОВАТЬ В ИГРУ</h1>
                <Link to={"/main"}>Вернуться на главную</Link>
                <p className="text-burgundy-200">Убедите игроков в своей ценности и попадите в бункер!</p>
            </header>
            <section className="mb-12">
                <BunkerInformation roomData={roomData} />
            </section>
            <section>
                {user && <CharactersTable roomData={roomData} canOpen={true} user={user}/>}
            </section>
            <section>
                {user && <PollInformation user={user} canOpen={true} roomData={roomData}/>}
            </section>

        </div>
    );
}