import {useGetRoomStateQuery} from "../../../store/types.generated";
import {useEffect} from "react";
import {BunkerInformation} from "../../../components/BunkerInformation";
import {CharactersTable} from "../../../components/CharactersTable";
import {useParams} from "react-router-dom";

export function ClosedRoom(){
    const { roomId: roomIdStr } = useParams<{ roomId: string }>()
    const roomId = Number(roomIdStr)
    const {data: roomData, refetch: refetchRoomData } = useGetRoomStateQuery({roomId: roomId})

    useEffect(() => {
        const intervalId = setInterval(refetchRoomData, 200)
        return () => clearInterval(intervalId)
    }, [refetchRoomData])

    return (
        <div className="min-h-screen bg-burgundy-900 text-white p-8">
            <header className="text-center mb-8">
                <h1 className="text-2xl font-bold text-burgundy-300 mb-4">ДОБРО ПОЖАЛОВАТЬ В ИГРУ</h1>
                <p className="text-burgundy-200">Убедите игроков в своей ценности и попадите в бункер!</p>
            </header>
            <section className="mb-12">
                <BunkerInformation roomData={roomData} />
            </section>
            <section>
                <CharactersTable roomData={roomData} canOpen={false} />
            </section>
        </div>
    );
}