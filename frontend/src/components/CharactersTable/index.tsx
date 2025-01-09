import {RoomMessage, useGetAllFactsQuery, useGetApiMeQuery, useOpenFactMutation} from "../../store/types.generated";

function UserFact() {
    return <div></div>
}

export function CharactersTable({roomData, canOpen}: { roomData?: RoomMessage, canOpen: boolean }) {
    const {data: user} = useGetApiMeQuery();
    const characterId = roomData?.characters
        ?.find(character => character?.user?.id === user?.id)
        ?.id ?? 0
    const {data: userFacts} = useGetAllFactsQuery({characterId: characterId})
    const userOpenFacts = roomData?.characters
        ?.find(character => character?.id === characterId)
        ?.openedFacts
    const [openFact] = useOpenFactMutation()

    return <div>
        <table className={"table-auto border border-black border-collapse"}>
            <thead>
            <tr>
                <td className={"border border-slate-600"}>Имя игрока</td>
                <td className={"border border-slate-600"}>Имя персонажа</td>
                <td className={"border border-slate-600"}>Телосложение</td>
                <td className={"border border-slate-600"}>Профессия</td>
                <td className={"border border-slate-600"}>Здоровье</td>
                <td className={"border border-slate-600"}>Хобби</td>
                <td className={"border border-slate-600"}>Фобия</td>
                <td className={"border border-slate-600"}>Особенность характера</td>
                <td className={"border border-slate-600"}>Инструмент</td>
                <td className={"border border-slate-600"}>Инвентарь</td>
            </tr>
            </thead>
            {roomData?.characters?.map(character =>
                <tr>
                    <td className={"border border-slate-600"}>{character.user?.username}</td>
                    <td className={"border border-slate-600"}>{character.name}</td>
                    <td className={"border border-slate-600"}>{character.openedFacts?.bodyType}</td>
                    <td className={"border border-slate-600"}>{character.openedFacts?.profession}</td>
                    <td className={"border border-slate-600"}>{character.openedFacts?.health}</td>
                    <td className={"border border-slate-600"}>{character.openedFacts?.hobby}</td>
                    <td className={"border border-slate-600"}>{character.openedFacts?.phobia}</td>
                    <td className={"border border-slate-600"}>{character.openedFacts?.trait}</td>
                    <td className={"border border-slate-600"}>{character.openedFacts?.equipment}</td>
                    <td className={"border border-slate-600"}>{character.openedFacts?.bag}</td>
                </tr>
            )}
            {canOpen &&
                <tr>
                    <td className={"border border-slate-600 font-bold"}>Ваш персонаж:</td>
                    <td className={"border border-slate-600"}>{userFacts?.name}</td>
                    <td className={`border border-slate-600 ${userOpenFacts?.bodyType === undefined ? "" : "opacity-50"}`}>
                        <button onClick={() => openFact({
                            characterId: characterId,
                            factType: "BODY_TYPE"
                        })} className={"hover:underline"}
                        >{userFacts?.bodyType}</button>
                    </td>
                    <td className={`border border-slate-600 ${userOpenFacts?.profession === undefined ? "" : "opacity-50"}`}>
                        <button onClick={() => openFact({
                            characterId: characterId,
                            factType: "PROFESSION"
                        })} className={"hover:underline"}
                        >{userFacts?.profession}</button>
                    </td>
                    <td className={`border border-slate-600 ${userOpenFacts?.health === undefined ? "" : "opacity-50"}`}>
                        <button onClick={() => openFact({
                            characterId: characterId,
                            factType: "HEALTH"
                        })} className={"hover:underline"}
                        >{userFacts?.health}</button>
                    </td>
                    <td className={`border border-slate-600 ${userOpenFacts?.hobby === undefined ? "" : "opacity-50"}`}>
                        <button onClick={() => openFact({
                            characterId: characterId,
                            factType: "HOBBY"
                        })} className={"hover:underline"}
                        >{userFacts?.hobby}</button>
                    </td>
                    <td className={`border border-slate-600 ${userOpenFacts?.phobia === undefined ? "" : "opacity-50"}`}>
                        <button onClick={() => openFact({
                            characterId: characterId,
                            factType: "PHOBIA"
                        })} className={"hover:underline"}
                        >{userFacts?.phobia}</button>
                    </td>
                    <td className={`border border-slate-600 ${userOpenFacts?.trait === undefined ? "" : "opacity-50"}`}>
                        <button onClick={() => openFact({
                            characterId: characterId,
                            factType: "TRAIT"
                        })} className={"hover:underline"}
                        >{userFacts?.trait}</button>
                    </td>
                    <td className={`border border-slate-600 ${userOpenFacts?.equipment === undefined ? "" : "opacity-50"}`}>
                        <button onClick={() => openFact({
                            characterId: characterId,
                            factType: "EQUIPMENT"
                        })} className={"hover:underline"}
                        >{userFacts?.equipment}</button>
                    </td>
                    <td className={`border border-slate-600 ${userOpenFacts?.bag === undefined ? "" : "opacity-50"}`}>
                        <button onClick={() => openFact({
                            characterId: characterId,
                            factType: "BAG"
                        })} className={"hover:underline"}
                        >{userFacts?.bag}</button>
                    </td>
                </tr>
            }
        </table>
    </div>
}